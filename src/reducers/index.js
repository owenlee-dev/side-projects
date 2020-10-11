import { combineReducers } from "redux";
import AppReducer from "../components/AppSlice";

export default combineReducers({
  app: AppReducer,
});
