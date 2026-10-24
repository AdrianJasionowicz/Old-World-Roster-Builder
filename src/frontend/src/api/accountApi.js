import API from "./api";

export const getAccount = () =>
    API.get("/me");

export const changeUsername = (data) =>
    API.patch("/account/username", data);

export const changeEmail = (data) =>
    API.patch("/account/email", data);

export const changePassword = (data) =>
    API.patch("/account/password", data);

export const deleteAccount = (data) =>
    API.delete("/account", {
        data
    });
