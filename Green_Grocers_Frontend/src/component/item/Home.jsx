import React from 'react'
import About from './About';
import Categories from './Categories';
import ExploreProduct from './ExploreProduct';
import HomeCorousel from './HomeCorousel';
import '../style/Item.css';

function Home() {

    return (
        <>
            <HomeCorousel/>
            <Categories/>
            <ExploreProduct/>

        </>

    )
}

export default Home