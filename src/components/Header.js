import React, { useState } from "react";
import {
  getSearched,
  setUserInput,
  setCurrentPage,
  setGenre,
  getFeatured,
} from "../components/AppSlice.js";
import { useSelector, useDispatch } from "react-redux";

//user search occurs here
const Header = () => {
  const dispatch = useDispatch();
  const { userInput, currentPage } = useSelector((state) => state.app);

  //on search, set page to 1 and return results
  const submitFormHandler = (e) => {
    e.preventDefault();
    dispatch(setGenre(null));
    dispatch(setCurrentPage(1));
    const toSend = { userInput: userInput, pageNumber: currentPage };
    dispatch(getSearched(toSend));
  };

  const inputChangeHandler = (e) => {
    dispatch(setUserInput(e.target.value));
  };

  const clickTitleHandler = () => {
    dispatch(getFeatured(1));
    dispatch(setGenre(null));
    dispatch(setUserInput(""));
  };

  return (
    <div className="Header">
      <a
        href="#"
        onClick={() => {
          clickTitleHandler();
        }}
      >
        <h1 className="title">Nertflorx</h1>
      </a>
      <form onSubmit={submitFormHandler}>
        <input
          className="search"
          type="search"
          value={userInput}
          placeholder="Search..."
          onChange={inputChangeHandler}
        ></input>
      </form>
    </div>
  );
};

export default Header;
