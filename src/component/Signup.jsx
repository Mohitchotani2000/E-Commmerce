import React, { useEffect, useState } from 'react'
import { NavLink, useNavigate } from 'react-router-dom'
import { signup } from '../services/user-service'
import { toast } from 'react-toastify'
import { cloneDeep } from 'lodash'

export default function Signup() {
    const initial_state = {
        name: '',
        username: '',
        email: '',
        password: '',
        userAddressList: [
            {
                address: '',
            }
        ],

    }

    let navigate = useNavigate();
    const [data, setData] = useState(initial_state)

    useEffect(() => {

    }, [data])

    const [error, setError] = useState({
        errors: {},
        isError: false,
    });

    const handleChange = (event, property) => {

        setData({ ...data, [property]: event.target.value })
    }
    const handleAddressChange = (event, index) => {
        let tempdata = cloneDeep(data);
        tempdata.userAddressList[index].address = event.target.value
        setData(tempdata);
    };


    const submitForm = (event) => {
        event.preventDefault();
        if (error.isError) {
            toast.error("Form data is invalid, fill correct details !!");
            return;
        }
        signup(data).then((resp) => {
            toast.success("User registered successfully")
            setData({
                name: '',
                username: '',
                email: '',
                password: '',
                userAddressList: [
                    {
                        address: '',
                    }
                ],
            })
            event.target.reset();
            navigate("/login");
            
            // console.log(data)
        }).catch((error) => {
            console.log(error);
            setError({
                errors: error,
                isError: true
            })

        })

    }

    return (
        <>
            <section className="vh-100 bg-image">
                <div className="mask d-flex align-items-center h-100 gradient-custom-3">
                    <div className="container h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-9 col-lg-7 col-xl-6">
                                <div className="card border-dark" style={{ borderRadius: "15px" }}>
                                    <div className="card-body p-5">
                                        <h2 className="text-uppercase text-center mb-5">Create an account</h2>

                                        <form onSubmit={submitForm}>

                                            <div className="form-outline mb-4">
                                                <label className="form-label" htmlFor="name">Your Name</label>
                                                <input type="text" id="name" className="form-control form-control-lg" onChange={(e) => handleChange(e, 'name')}
                                                    value={data.name} />
                                            </div>

                                            <div className="form-outline mb-4">
                                                <label className="form-label" htmlFor="username">Your Username</label>
                                                <input type="text" id="username" className="form-control form-control-lg" onChange={(e) => handleChange(e, 'username')}
                                                    value={data.username} />
                                            </div>

                                            <div className="form-outline mb-4">
                                                <label className="form-label" htmlFor="email">Your Email</label>
                                                <input type="email" id="email" className="form-control form-control-lg" onChange={(e) => handleChange(e, 'email')}
                                                    value={data.email} />
                                            </div>

                                            <div className="form-outline mb-4">
                                                <label className="form-label" htmlFor="password">Password</label>
                                                <input type="password" id="password" className="form-control form-control-lg" onChange={(e) => handleChange(e, 'password')}
                                                    value={data.password} />
                                            </div>

                                            <div className="form-outline mb-4">
                                                <label className="form-label" htmlFor="address">Your Address</label>
                                                <textarea type="text" id="address" className="form-control form-control-lg" onChange={(e) => handleAddressChange(e, 0)}
                                                    value={data.userAddressList.address} />
                                            </div>

                                            <div className="d-flex justify-content-center">
                                                <button type="Submit"
                                                    className="btn btn-outline-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
                                            </div>

                                            <p className="text-center text-muted mt-5 mb-0">Have already an account? <NavLink to="/login"
                                                className="fw-bold text-body"><u>Login here</u></NavLink></p>

                                        </form>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}
