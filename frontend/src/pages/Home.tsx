import React from 'react';
import useGetHelloWorld from "../hooks/use-get-helloworld";

const Home: React.FC = () => {
    const { hello } = useGetHelloWorld();
    return (
        <>
          <h1>Home</h1>
          <p>{hello}</p>
        </>
        );

};

export default Home;