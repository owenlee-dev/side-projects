import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import api from "../api/api.js";

const initialState = {
  movieData: [],
  movieGenres: [],
  userInput: null,
  selectedGenre: null,
  currentPage: 1,
};

export const getFeatured = createAsyncThunk("app/getFeatured", async (page) => {
  const response = await api.getFeatured(page);
  return response;
});

export const getSearched = createAsyncThunk(
  "app/getSearched",
  async (props) => {
    const { userInput, pageNumber } = props;
    const response = await api.getSearchResults(userInput, pageNumber);
    return response;
  }
);

export const getGenres = createAsyncThunk("app/getGenres", async () => {
  const response = await api.getGenreList();
  return response;
});

export const getGenre = createAsyncThunk("app/getGenre", async (props) => {
  const { genreId, pageNumber } = props;
  const response = await api.getChosenGenre(genreId, pageNumber);
  return response;
});

const AppSlice = createSlice({
  name: "app",
  initialState,
  reducers: {
    setUserInput(state, action) {
      state.userInput = action.payload;
    },
    setGenre(state, action) {
      state.selectedGenre = action.payload;
    },
    setCurrentPage(state, action) {
      state.currentPage = action.payload;
    },
  },
  extraReducers: {
    [getFeatured.fulfilled]: (state, action) => {
      state.pending = false;
      state.movieData = action.payload;
    },
    [getFeatured.rejected]: (state, action) => {
      state.pending = false;
      state.error = action.error;
    },
    [getFeatured.pending]: (state, action) => {
      state.pending = true;
    },
    [getSearched.fulfilled]: (state, action) => {
      state.pending = false;
      state.movieData = action.payload;
    },
    [getSearched.rejected]: (state, action) => {
      state.pending = false;
      state.error = action.error;
    },
    [getSearched.pending]: (state, action) => {
      state.pending = true;
    },
    [getGenres.fulfilled]: (state, action) => {
      state.pending = false;
      state.movieGenres = action.payload;
    },
    [getGenres.rejected]: (state, action) => {
      state.pending = false;
      state.error = action.error;
    },
    [getGenres.pending]: (state, action) => {
      state.pending = true;
    },
    [getGenre.fulfilled]: (state, action) => {
      state.pending = false;
      state.movieData = action.payload;
    },
    [getGenre.rejected]: (state, action) => {
      state.pending = false;
      state.error = action.error;
    },
    [getGenre.pending]: (state, action) => {
      state.pending = true;
    },
  },
});

export const { setUserInput, setGenre, setCurrentPage } = AppSlice.actions;

export default AppSlice.reducer;
