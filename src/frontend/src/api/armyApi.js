import axios from "axios";

const API = axios.create({

    baseURL: "http://localhost:8080",

    withCredentials: true

});

export const getArmies = () => API.get("/armies");

export const getArmy = (id) => API.get(`/armies/${id}`);

export const getArmyPoints = (id) =>
    API.get(`/armies/${id}/points`);

export const createArmy = (army) =>
    API.post("/armies", army);

export const deleteArmy = (id) =>
    API.delete(`/armies/${id}`);

export const renameArmy = (id, name) =>
    API.patch(`/armies/${id}/name`, null, {
        params: {
            newName: name
        }
    });

export const addUnitToArmy = (armyId, unitId) =>
    API.post(`/armies/${armyId}/units/${unitId}`);

export const deleteArmyUnit = (armyUnitId) =>
    API.delete(`/army-units/${armyUnitId}`);

export const increaseUnitSize = (armyUnitId) =>
    API.post(`/army-units/${armyUnitId}/increase`);

export const decreaseUnitSize = (armyUnitId) =>
    API.post(`/army-units/${armyUnitId}/decrease`);

export const getArmyUnitUpgrades = (armyUnitId) =>
    API.get(`/armyUnit/upgrades/${armyUnitId}`);

export const selectUpgrade = (armyUnitId, upgradeId) =>
    API.post(`/armyUnit/${armyUnitId}/upgrades/${upgradeId}`);

export const removeUpgrade = (armyUnitId, upgradeId) =>
    API.delete(`/armyUnit/${armyUnitId}/upgrades/${upgradeId}`);

export const exportArmyPdf = (armyName, armyId) =>
    API.post(
        "/exportPdf",
        {
            armyName: armyName,
            armyId: armyId
        },
        {
            responseType: "blob"
        }
    );