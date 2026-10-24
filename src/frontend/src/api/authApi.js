import API from "./api";

export const login = (data) => API.post("/login", data);

export const register = (data) => API.post("/register", data);

export const me = () => API.get("/me");

export const logout = () => API.post("/logout");
