import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
  getGenres,
  setGenre,
  getGenre,
  setCurrentPage,
  setUserInput,
} from "./AppSlice";
import { AiOutlineHome, AiOutlineClose, AiOutlineLaptop } from "react-icons/ai";
import {
  FaBars,
  FaUserNinja,
  FaLaughBeam,
  FaRegKissBeam,
} from "react-icons/fa";
import {
  GiSwordman,
  GiBabyFace,
  GiPistolGun,
  GiPineTree,
  GiDramaMasks,
  GiFamilyHouse,
  GiElfEar,
  GiScrollUnfurled,
  GiScreaming,
  GiMusicalNotes,
  GiMagnifyingGlass,
  GiMaterialsScience,
  GiHalfDead,
  GiGreatWarTank,
  GiCowboyBoot,
} from "react-icons/gi";

//genre selection occurs here
const Sidebar = () => {
  const dispatch = useDispatch();
  const [sidebar, setSidebar] = useState(false);
  const showSidebar = () => setSidebar(!sidebar);
  const { movieGenres, currentPage } = useSelector((state) => state.app);
  const icons = [
    FaUserNinja,
    GiSwordman,
    GiBabyFace,
    FaLaughBeam,
    GiPistolGun,
    GiPineTree,
    GiDramaMasks,
    GiFamilyHouse,
    GiElfEar,
    GiScrollUnfurled,
    GiScreaming,
    GiMusicalNotes,
    GiMagnifyingGlass,
    FaRegKissBeam,
    GiMaterialsScience,
    AiOutlineLaptop,
    GiHalfDead,
    GiGreatWarTank,
    GiCowboyBoot,
  ];

  useEffect(() => {
    dispatch(getGenres());
  }, []);

  //select genre
  const genreSelectionHandler = (genreId) => {
    dispatch(setUserInput(null));
    dispatch(setCurrentPage(1));
    const toSend = { genreId: genreId, pageNumber: currentPage };
    dispatch(getGenre(toSend));
    dispatch(setGenre(genreId));
    setSidebar(!sidebar);
  };
  return (
    <>
      <div className="navbar">
        <button className="menu-bars" onClick={showSidebar}>
          <FaBars className="bars-icon" />
        </button>
      </div>
      <nav className={sidebar ? "nav-menu active" : "nav-menu"}>
        <ul className="sidebar-list">
          <li className="navbar-toggle">
            <button className="xbutton" onClick={showSidebar}>
              <AiOutlineClose />
            </button>
          </li>
          {movieGenres.map((genre, index) => {
            const Icon = icons[index];
            return (
              <li key={genre.id} className="nav-text">
                <button
                  className="genre-btn"
                  onClick={() => genreSelectionHandler(genre.id)}
                >
                  <Icon className="genre-icon" />
                  <span className="nav-item-title">{genre.name}</span>
                </button>
              </li>
            );
          })}
        </ul>
      </nav>
    </>
  );
};

export default Sidebar;
