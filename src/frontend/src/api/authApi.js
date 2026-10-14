import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true
});

export const login = (data) => api.post("/login", data);

export const register = (data) => api.post("/register", data);

export const me = () => api.get("/me");

export const logout = () => api.post("/logout");