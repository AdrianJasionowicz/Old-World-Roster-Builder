package com.oldworld.rosterbuilder.loginUser;

import com.oldworld.rosterbuilder.army.Army;
import com.oldworld.rosterbuilder.army.ArmyRepository;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnit;
import com.oldworld.rosterbuilder.armyUnit.ArmyUnitRepository;
import com.oldworld.rosterbuilder.exception.ForbiddenException;
import com.oldworld.rosterbuilder.exception.NotFoundException;
import com.oldworld.rosterbuilder.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrentUserService {

    private final LoginUserRepository loginUserRepository;
    private final ArmyRepository armyRepository;
    private final ArmyUnitRepository armyUnitRepository;

    public LoginUser getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User not logged in");
        }
        return loginUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("User not logged in"));
    }

    public Army getArmyOwnedByCurrentUser(Long armyId) {
        var army = armyRepository.findById(armyId)
                .orElseThrow(() -> new NotFoundException("Army with id " + armyId + " not found"));
        validateArmyAccess(army);
        return army;
    }

    public ArmyUnit getArmyUnitOwnedByCurrentUser(Long armyUnitId) {
        var armyUnit = armyUnitRepository.findById(armyUnitId)
                .orElseThrow(() -> new NotFoundException("Army unit not found with id: " + armyUnitId));
        validateArmyAccess(armyUnit.getArmy());
        return armyUnit;
    }

    void validateArmyAccess(Army army) {
        var loginUser = getCurrentUser();
        if (!army.getOwner().getId().equals(loginUser.getId())) {
            log.warn("User '{}' tried to access army {} of another user", loginUser.getUsername(), army.getId());
            throw new ForbiddenException("Access denied");
        }
    }
}
