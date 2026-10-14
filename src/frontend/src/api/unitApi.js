import API from "./api";

export const getUnits = (faction) =>
    API.get("/units/faction", {
        params: {
            faction
        }
    });

export const getUnit = (id) =>
    API.get(`/units/${id}`);