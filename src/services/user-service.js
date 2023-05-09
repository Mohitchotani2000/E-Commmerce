import axios from "axios"

export const signup = async (user) => {
    return await axios
    .post('http://localhost:8081/users/auth/register', user)
    .then((response) => response.data);
}

export const loginUser = async (loginDetail) => {
    return await axios
    .post('http://localhost:8081/users/auth/login', loginDetail)
    .then((response) => response.data);
}