import API from "./api";

export const getAccount = () =>
    API.get("/me");

export const changeUsername = (data) =>
    API.post("/changeUsername", data);

export const changeEmail = (data) =>
    API.post("/changeEmail", data);

export const changePassword = (data) =>
    API.post("/changePassword", data);

export const deleteAccount = (data) =>
    API.delete("/deleteAccount", {
        data
    });