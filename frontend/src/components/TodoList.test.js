import { render, screen } from "@testing-library/react";
import TodoList from "./TodoList";

test("renders todo list", () => {
	render(<TodoList />);
	const element = screen.getByText(/Todo List/i);
	expect(element).toBeInTheDocument();
});
