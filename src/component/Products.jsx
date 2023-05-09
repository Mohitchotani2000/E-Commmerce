import React, { useEffect, useState } from 'react'
import axios from 'axios';
import Skeleton from 'react-loading-skeleton';
import { NavLink } from 'react-router-dom';

export default function Products() {

    const [products, setProducts] = useState([]);
    const [filter, setFilter] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const cachedData = localStorage.getItem('products');
        if (cachedData) {
            setProducts(JSON.parse(cachedData));
            setFilter(JSON.parse(cachedData));
        } else {
            getProducts();
        }
    }, []);

    const getProducts = async () => {
        setLoading(true);
        const response = await axios.get("http://localhost:8082/product-service/products");
        setProducts(response.data);
        setFilter(response.data);
        setLoading(false);
        localStorage.setItem('products', JSON.stringify(response.data));
    };

    const Loading = () => {
        return (
            <>
                <div className="col-md-3">
                    <Skeleton height={350} />
                </div>
                <div className="col-md-3">
                    <Skeleton height={350} />
                </div>
                <div className="col-md-3">
                    <Skeleton height={350} />
                </div>
                <div className="col-md-3">
                    <Skeleton height={350} />
                </div>
            </>
        )
    }

    const filterProduct = (cat) => {
        const updatedList = products.filter((x) => x.category.toLowerCase() === cat);
        setFilter(updatedList);
    }


    const ShowProducts = () => {
        return (

            <>
                <div className="buttons d-flex justify-content-center mb-5 pb-5">
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => setFilter(products)}>All</button>
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => filterProduct("men's clothing")}>Men's Clothing</button>
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => filterProduct("women's clothing")}>Women's Clothing</button>
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => filterProduct("electronic")}>Electronic</button>
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => filterProduct("jewelry")}>Jewelry</button>
                    <button className="btn btn-outline-dark me-2"
                        onClick={() => filterProduct("stationary")}>Stationary</button>
                </div>
                {filter.map((product) => {
                    return (
                        <React.Fragment key={product.productId}>
                            <div className="col-md-3 mb-4">
                                <div className="card h-100 text-center p-4" >
                                    <img src={product.productUrl} className="card-img-top" alt={product.productName} height="150px" />
                                    <div className="card-body">
                                        <h5 className="card-title mb-0">{product.productName}</h5>
                                        <p className="card-text lead fw-bold">₹ {product.productPrice}</p>
                                        <NavLink to={`/products/${product.productId}`} className="btn btn-outline-primary">Buy Now</NavLink>
                                    </div>
                                </div>
                            </div>
                        </React.Fragment>
                    )
                })}
            </>
        )

    }


    return (
        <>
            <div>
                <div className="container my-5 py-5">
                    <div className="row">
                        <div className="col-12 mb-5">
                            <h1 className="display-6 fw-bolder text-center">Latest Products</h1>
                            <hr />
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        {loading ? <Loading /> : <ShowProducts />}
                    </div>
                </div>
            </div>
        </>
    )
}
