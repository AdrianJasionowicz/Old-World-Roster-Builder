package com.armybuilderv2.armyBuilderV2.unit;

import com.armybuilderv2.armyBuilderV2.exception.UnitNotFoundException;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitRequest;
import com.armybuilderv2.armyBuilderV2.unit.model.UnitResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    public UnitResponse addUnit(UnitRequest unitRequest) {
        Unit unit = unitMapper.mapUnitRqToUnit(unitRequest);
        unitRepository.save(unit);
        return unitMapper.mapUnitToUnitResponse(unit);
    }

    public List<UnitResponse> getAllByFaction(String faction) {
        UnitFaction factionToSearch = UnitFaction.valueOf(faction);
        List<Unit> unitList = unitRepository.getAllByUnitFaction(factionToSearch);
        return unitList
                .stream()
                .map(unitMapper::mapUnitToUnitResponse)
                .toList();
    }

    public UnitResponse getUnitById(Long id) {
        Unit unit = unitRepository.findById(id).orElseThrow(()-> new UnitNotFoundException("Unit with id " + id + " not found"));
        return unitMapper.mapUnitToUnitResponse(unit);
    }

    public void deleteById(Long id) {
        if(unitRepository.existsById(id)) {
            unitRepository.deleteById(id);
        } else {
            throw new UnitNotFoundException("Unit with id " + id + " not found");
        }
    }
}