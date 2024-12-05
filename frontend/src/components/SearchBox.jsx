import React from "react";
import { InputBase } from "@mui/material";
import { styled } from "@mui/material/styles";
import SearchIcon from "@mui/icons-material/Search";

const Search = styled("div")(({ theme }) => ({
	position: "relative",
	borderRadius: theme.shape.borderRadius,
	backgroundColor: theme.palette.grey[200],
	marginRight: theme.spacing(2),
	marginLeft: 0,
	width: "100%",
	display: "flex",
	alignItems: "center",
	padding: theme.spacing(0.5, 1),
}));

const SearchBox = () => {
	return (
		<Search>
			<SearchIcon />
			<InputBase placeholder="Search…" inputProps={{ "aria-label": "search" }} sx={{ ml: 1, flex: 1 }} />
		</Search>
	);
};

export default SearchBox;
