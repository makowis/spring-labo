import { useEffect, useState } from 'react';
import axios from 'axios';

interface GetHelloWorldState {
    error: Error | null | unknown;
    loading: boolean;
    hello: string;
}

const useGetHelloWorld = (): GetHelloWorldState => {
    const [state, setState] = useState<GetHelloWorldState>({
        error: null,
        loading: true,
        hello: ""
    });

    useEffect(() => {
        (async () => {
            try {
                const res = await  axios.get(`/api/hello`);
                setState({
                    ...state,
                    loading: false,
                    hello: res.data,
                });
            } catch (error) {
                setState({...state, error, loading: false });
            }
        })();
    },[])

    return { ...state };
};

export default useGetHelloWorld;