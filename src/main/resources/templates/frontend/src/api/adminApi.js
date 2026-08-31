import axios from "axios";

const API = "http://localhost:8080";

export function addUnit(data) {

    return axios.post(

        `${API}/units`,
        data,
        {
            withCredentials: true
        }

    );

}