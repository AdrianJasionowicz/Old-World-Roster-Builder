package com.oldworld.rosterbuilder.upgrade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpgradeServiceTest {

    @Mock
    UpgradeRepository upgradeRepository;
    @InjectMocks
    UpgradeService upgradeService;

    @Test
    @DisplayName("findOrCreate returns an upgrade that is already in the catalog")
    void findOrCreateExisting() {
        var shield = new Upgrade("Shield", UpgradeType.UNIT_EQUIPMENT, "Shields");
        when(upgradeRepository.findByNameAndUpgradeType("Shield", UpgradeType.UNIT_EQUIPMENT)).thenReturn(Optional.of(shield));

        assertSame(shield, upgradeService.findOrCreate("Shield", UpgradeType.UNIT_EQUIPMENT, "Other description"));
        verify(upgradeRepository, never()).save(any());
    }

    @Test
    @DisplayName("findOrCreate saves a new upgrade with a trimmed name")
    void findOrCreateNew() {
        when(upgradeRepository.findByNameAndUpgradeType("Shield", UpgradeType.UNIT_EQUIPMENT)).thenReturn(Optional.empty());
        when(upgradeRepository.save(any(Upgrade.class))).then(returnsFirstArg());

        var upgrade = upgradeService.findOrCreate("  Shield ", UpgradeType.UNIT_EQUIPMENT, "Shields");

        assertEquals("Shield", upgrade.getName());
        assertEquals(UpgradeType.UNIT_EQUIPMENT, upgrade.getUpgradeType());
        assertEquals("Shields", upgrade.getDescription());
        verify(upgradeRepository).save(upgrade);
    }

    @Test
    @DisplayName("findOrCreate treats the same name of another type as a new upgrade")
    void findOrCreateOtherType() {
        when(upgradeRepository.findByNameAndUpgradeType("Shield", UpgradeType.MAGIC_ARMOUR)).thenReturn(Optional.empty());
        when(upgradeRepository.save(any(Upgrade.class))).then(returnsFirstArg());

        assertEquals(UpgradeType.MAGIC_ARMOUR, upgradeService.findOrCreate("Shield", UpgradeType.MAGIC_ARMOUR, null).getUpgradeType());
    }

    @Test
    @DisplayName("findOrCreate passes a database error on")
    void findOrCreateDatabaseError() {
        when(upgradeRepository.findByNameAndUpgradeType("Shield", UpgradeType.UNIT_EQUIPMENT)).thenReturn(Optional.empty());
        when(upgradeRepository.save(any(Upgrade.class))).thenThrow(new DataIntegrityViolationException("duplicate"));

        assertThrows(DataIntegrityViolationException.class, () -> upgradeService.findOrCreate("Shield", UpgradeType.UNIT_EQUIPMENT, "Shields"));
    }

    @Test
    @DisplayName("findOrCreate without a name throws NullPointerException")
    void findOrCreateNullName() {
        assertThrows(NullPointerException.class, () -> upgradeService.findOrCreate(null, UpgradeType.UNIT_EQUIPMENT, "Shields"));
        verifyNoInteractions(upgradeRepository);
    }
}
