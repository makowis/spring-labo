import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Home from './pages/Home'
import Article from "./pages/Article";
import Write from "./pages/Write";

function App() {
  return (
      <BrowserRouter>
        <Routes>
          <Route index element={<Home />} />
          <Route path="article" element={<Article />} />
          <Route path="edit" element={<Write />} />
        </Routes>
      </BrowserRouter>
  );
}

export default App;
