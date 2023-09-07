async function MakeRequest(
    method: 'GET'|'POST',
    url: string,
    data:  object|FormData,
    responseType: 'json' | 'text'
) {
    try {
        const requestOptions: any = {
            method,
            headers: {},
        }
        if (data !== null) {
  
            if (method === "GET" && data instanceof Object ) {
                requestOptions.method = "GET";
                const params = new URLSearchParams(JSON.stringify(data));
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

    } catch (e) {
        throw new Error("Request problems")
    }
}
export { MakeRequest };