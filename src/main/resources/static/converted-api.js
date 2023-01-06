async function getUuids() {
    try {
        const response = await fetch('/converted');
        const data = await response.json();
        return data.convertables.map(convertable => convertable.uuid);
    } catch (error) {
        console.error(error);
    }
}

async function updateResults() {
    const uuids = await getUuids();
    const container = document.getElementById('conversionResults');

    function addLipsyncDiv(uuid) {
        const divLipsync = document.createElement('div');
        divLipsync.className = 'item';

        const anchorLipSync = document.createElement('a');
        anchorLipSync.href = `/converted/${uuid}.json`;
        anchorLipSync.innerHTML = 'Download Lipsync file';

        divLipsync.appendChild(anchorLipSync);
        return divLipsync;
    }

    function addWavDiv(uuid) {
        const divWav = document.createElement('div');
        divWav.className = 'item';

        const anchorWav = document.createElement('a');
        anchorWav.href = `/converted/${uuid}.wav`;
        anchorWav.innerHTML = 'Download Lipsync file';
        divWav.appendChild(anchorWav);
        return divWav;
    }

    for (const uuid of uuids) {
        const divLipsync = addLipsyncDiv(uuid);
        const divWav = addWavDiv(uuid);

        const divButtonRemove = document.createElement('div');
        divButtonRemove.className = 'item';

        const buttonRemove = document.createElement('button');
        buttonRemove.className = 'mdc-button mdc-button raised mdc-theme primary-bg'
        buttonRemove.innerHTML = 'Remove';
        divButtonRemove.appendChild(buttonRemove);

        container.appendChild(divLipsync);
        container.appendChild(divWav);
        container.appendChild(divButtonRemove);
    }
}
