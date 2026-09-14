package com.armybuilderv2.armyBuilderV2.loginUser;

import com.armybuilderv2.armyBuilderV2.army.Army;
import com.armybuilderv2.armyBuilderV2.army.ArmyRepository;
import com.armybuilderv2.armyBuilderV2.exception.ArmyAccessDeniedException;
import com.armybuilderv2.armyBuilderV2.exception.ArmyNotFoundException;
import com.armybuilderv2.armyBuilderV2.exception.UserNoLoggedInException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    private final LoginUserRepository loginUserRepository;
    private final ArmyRepository armyRepository;

    public CurrentUserService(LoginUserRepository loginUserRepository, ArmyRepository armyRepository) {
        this.loginUserRepository = loginUserRepository;
        this.armyRepository = armyRepository;
    }

    public LoginUser getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserNoLoggedInException("User not logged in");
        }

        String username = authentication.getName();

        return loginUserRepository.findByUsername(username).orElseThrow(() -> new UserNoLoggedInException("User Not Logged In"));

    }

    public void validateArmyAccess(Army army) {
        LoginUser loginUser = getCurrentUser();
        boolean hasAccessToArmy = army.getOwner().equals(loginUser);
        if (!hasAccessToArmy) {
            throw new ArmyAccessDeniedException("Access denied");
        }
    }

    public Army getArmyOwnedByCurrentUser(Long armyId) {
        Army army = getArmyEntityById(armyId);
        validateArmyAccess(army);
        return army;
    }


    public Army getArmyEntityById(Long armyId) {
        return armyRepository.findById(armyId).orElseThrow(() -> new ArmyNotFoundException("Army with id " + armyId + " not found"));
    }
}
