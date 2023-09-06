const MakeRequest = async (method: String, url: string, data: FormData | string, responseType:string) => {
    try {
        const requestOptions: any = {
            method,
            headers: {},
        }


        if (data !== null) {
            if (method === "POST") {
                requestOptions.body = data;
                requestOptions.method = "POST"

            }
            if (method === "GET") {
                requestOptions.method = "GET";
                let params;

                if (typeof data === 'string' || data instanceof String) {
                    params = new URLSearchParams(JSON.stringify(data));
                }
                if (params) {
                    url += `?${params.toString()}`;
                }

            }

            const response = await fetch(url, requestOptions);
            if (responseType.toLocaleLowerCase() === "json") {
                const json =await response.json();
                return  json;           }
            if (responseType.toLocaleLowerCase() === "text") {
                const text =  await response.text();
                return text;
            }
        }

    } catch (e) {
        throw new Error("Request problems")
    }
}

export  {MakeRequest};