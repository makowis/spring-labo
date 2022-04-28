import React from 'react';
import useGetHelloWorld from "../hooks/use-get-helloworld";
import {Link} from "react-router-dom";

const Home: React.FC = () => {
    const { hello } = useGetHelloWorld();
    return (
        <>
          <h1>Home</h1>
          <p>{hello}</p>
          <ul>
            <li><Link to="article">article</Link></li>
            <li><Link to="edit">edit</Link></li>
          </ul>
        </>
        );

};

export default Home;