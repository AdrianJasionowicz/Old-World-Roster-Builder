import API from "./api";

export const getArmies = () => API.get("/armies");

export const getArmy = (id) => API.get(`/armies/${id}`);

export const getArmyPoints = (id) => API.get(`/armies/${id}/points`);

export const createArmy = (army) => API.post("/armies", army);

export const deleteArmy = (id) => API.delete(`/armies/${id}`);

export const renameArmy = (id, name) => API.patch(`/armies/${id}/name`, { name });

export const changePointsLimit = (id, pointsLimit) => API.patch(`/armies/${id}/points-limit`, { pointsLimit });

export const addUnitToArmy = (armyId, unitId) => API.post(`/armies/${armyId}/units/${unitId}`);

export const addDetachment = (armyUnitId, unitId) => API.post(`/army-units/${armyUnitId}/detachments/${unitId}`);

export const deleteArmyUnit =(armyUnitId) => API.delete(`/army-units/${armyUnitId}`);

export const increaseUnitSize = (armyUnitId) => API.post(`/army-units/${armyUnitId}/increase`);

export const decreaseUnitSize = (armyUnitId) => API.post(`/army-units/${armyUnitId}/decrease`);

export const getArmyUnitUpgrades = (armyUnitId) => API.get(`/army-units/${armyUnitId}/upgrades`);

export const selectUpgrade = (armyUnitId, unitUpgradeId) =>
    API.post(`/army-units/${armyUnitId}/upgrades/${unitUpgradeId}`);

export const removeUpgrade = (armyUnitId, unitUpgradeId) =>
    API.delete(`/army-units/${armyUnitId}/upgrades/${unitUpgradeId}`);

export const exportArmyPdf = (armyId) => API.get(`/armies/${armyId}/pdf`, { responseType: "blob" });
