import API from "./api";

export const getUnits = (faction) =>
    API.get("/units", {
        params: {
            faction
        }
    });

export const getFactionsWithUnits = () => API.get("/units/factions");

export const getUnit = (id) =>
    API.get(`/units/${id}`);

export const deleteUnit = (id) =>
    API.delete(`/units/${id}`);