import axios from "axios";

const API = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true
});

const PUBLIC_PATHS = ["/login", "/register"];

API.interceptors.response.use(
    response => response,
    error => {
        const onPublicPage = PUBLIC_PATHS.includes(window.location.pathname);
        if (error.response?.status === 401 && !onPublicPage) {
            window.location.assign("/login");
        }
        return Promise.reject(error);
    }
);

export function errorMessage(error, fallback = "Something went wrong.") {
    const data = error?.response?.data;
    if (!data) {
        return error?.message === "Network Error" ? "Cannot reach the server." : fallback;
    }
    if (data.errors?.length) {
        return data.errors.map(e => `${e.field}: ${e.message}`).join(", ");
    }
    return data.message || fallback;
}

export default API;
