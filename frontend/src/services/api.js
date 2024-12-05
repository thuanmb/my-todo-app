// import axios from "axios";

// const API_URL = "http://localhost:8080/api/todos";

// export const fetchTodos = () => axios.get(API_URL);
// export const createTodo = (todo) => axios.post(API_URL, todo);

export const fetchTodosApi = async () => {
	return [
		{ id: 1, title: "Learn React", description: "Understand the basics of React" },
		{ id: 2, title: "Learn Redux", description: "State management with Redux" },
	];
};

export const updateTodo = async (todo) => {
	console.log("API Call to update TODO:", todo);
};

export const loginApi = async ({ username, password }) => {
	return new Promise((resolve) => {
		setTimeout(() => {
			if (username === "admin" && password === "password") {
				resolve({ success: true, user: { id: 1, name: "Admin" } });
			} else {
				resolve({ success: false });
			}
		}, 1000);
	});
};
