import axios from "axios";

const API_URL = "http://localhost:8080/api/todos";

export const fetchTodos = () => axios.get(API_URL);
export const createTodo = (todo) => axios.post(API_URL, todo);
