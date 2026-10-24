import API from "./api";

export const addUnit = (data) => API.post("/units", data);
