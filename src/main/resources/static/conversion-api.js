
const endpoint = '/conversion';

// POST request to send .wav file to conversion endpoint
async function sendWavFile(wavFile) {
    const formData = new FormData();
    formData.append('file', wavFile);

    const response = await fetch(endpoint, {
        method: 'POST',
        body: formData
    });

    const json = await response.json();
    return json.uuid;
}

// PUT request to update conversion with uuid
async function updateConversion(uuid) {
    const response = await fetch(`${endpoint}/${uuid}`, {
        method: 'PUT',
        body: JSON.stringify({
            status: 'processing'
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    });
}

// GET request to listen for server-sent events
async function listenForEvents(uuid) {
    const eventSource = new EventSource(`${endpoint}/${uuid}/events`);

    eventSource.onmessage = function(event) {
        console.log(event.data);
        const progress = event.data;
        const lipsyncConversionProgressBar = document.getElementById('lipsyncConversionProgress');
        var linearProgress = mdc.linearProgress.MDCLinearProgress.attachTo(lipsyncConversionProgressBar);
        linearProgress.progress = progress;
        if (progress >= 1) {
            eventSource.close();
            updateResults();
        }
    };
}


