import React, { useEffect } from "react";
import Movie from "./components/Movie";
import {
  getFeatured,
  getGenre,
  getSearched,
  setCurrentPage,
} from "./components/AppSlice";
import { useSelector, useDispatch } from "react-redux";
import Header from "./components/Header";
import Sidebar from "./components/Sidebar";
import Footer from "./components/Footer";

//main application
function App() {
  const dispatch = useDispatch();
  const { movieData, selectedGenre, userInput, currentPage } = useSelector(
    (state) => state.app
  );

  //on page load, get featured titles
  useEffect(() => {
    dispatch(getFeatured(1));
  }, []);

  //if going through search results, next page of search
  //if going through selected genre, next page of genre
  //if not then next page of featured
  const nextPageHandler = () => {
    if (selectedGenre) {
      const toSend = {
        genreId: selectedGenre,
        pageNumber: currentPage + 1,
      };
      dispatch(getGenre(toSend));
    } else if (userInput) {
      const toSend = { userInput: userInput, pageNumber: currentPage + 1 };
      dispatch(getSearched(toSend));
    } else {
      dispatch(getFeatured(currentPage + 1));
    }
    dispatch(setCurrentPage(currentPage + 1));
    window.scrollTo(0, 0);
  };
  const prevPageHandler = () => {
    if (selectedGenre) {
      const toSend = {
        genreId: selectedGenre,
        pageNumber: currentPage - 1,
      };
      dispatch(getGenre(toSend));
    } else if (userInput) {
      const toSend = { userInput: userInput, pageNumber: currentPage - 1 };
      dispatch(getSearched(toSend));
    } else {
      dispatch(getFeatured(currentPage - 1));
    }
    dispatch(setCurrentPage(currentPage - 1));
    console.log(currentPage);
    window.scrollTo(0, 0);
  };

  return (
    <div className="App">
      <Sidebar />
      <Header />
      <div className="content-wrapper">
        {movieData.length > 0 &&
          movieData.map((movie) => <Movie key={movie.id} {...movie} />)}
      </div>
      <div className="page-nav-btns">
        {currentPage > 1 && (
          <button className="page-nav-btn" onClick={prevPageHandler}>
            previous page
          </button>
        )}
        <button className="page-nav-btn" onClick={nextPageHandler}>
          next page
        </button>
      </div>
      <Footer />
    </div>
  );
}

export default App;
