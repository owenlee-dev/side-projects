import axios from "axios";

const API_KEY = "e5d5472bade839492cd6cd426951372d";

const getFeatured = (pageNumber) => {
  const FEATURED_API = `https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=${API_KEY}&page=${pageNumber}`;
  const toReturn = axios.get(FEATURED_API).then((res) => {
    const getData = res.data;
    return getData.results;
  });
  return toReturn;
};

const getSearchResults = (userInput, pageNumber) => {
  const SEARCH_API = `https://api.themoviedb.org/3/search/movie?api_key=${API_KEY}&language=en-US&page=${pageNumber}&query=`;
  const toReturn = axios.get(SEARCH_API + userInput).then((res) => {
    const getData = res.data;
    return getData.results;
  });
  return toReturn;
};

const getGenreList = () => {
  const GENRE_LIST_API = `https://api.themoviedb.org/3/genre/movie/list?api_key=${API_KEY}&language=en-US`;
  const toReturn = axios.get(GENRE_LIST_API).then((res) => {
    return res.data.genres;
  });
  return toReturn;
};

const getChosenGenre = (genreId, pageNumber) => {
  const GENRE_API = `https://api.themoviedb.org/3/discover/movie?certification_country=US&api_key=${API_KEY}&with_genres=${genreId}&page=${pageNumber}`;
  const toReturn = axios.get(GENRE_API).then((res) => {
    return res.data.results;
  });

  return toReturn;
};

export default { getFeatured, getSearchResults, getGenreList, getChosenGenre };
