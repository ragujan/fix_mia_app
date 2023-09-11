async function MakeRequest(method, url, data, responseType, contentType) {
    try {
        const requestOptions = {
            method,
            headers: {
                'Content-Type': contentType
            },
        };
        if (data !== null) {
            if (method === "GET" && typeof data === 'object') {
                requestOptions.method = "GET";
                const params = new URLSearchParams(JSON.stringify(data));
                url += `?${params}`;
            }
            if (method === "POST") {
                requestOptions.method = "POST";
                requestOptions.body = data;
                console.log(requestOptions);
            }
            const response = await fetch(url, requestOptions);
            if (responseType.toLocaleLowerCase() === "json") {
                const json = await response.json();
                return json;
            }
            if (responseType.toLocaleLowerCase() === "text") {
                const text = await response.text();
                return text;
            }
        }
    }
    catch (e) {
        throw new Error("Request problems");
    }
}
export { MakeRequest };
