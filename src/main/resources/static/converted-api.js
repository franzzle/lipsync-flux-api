async function getUuids() {
    try {
        const response = await fetch('/converted');
        const data = await response.json();
        return data.convertables.map(convertable => convertable.uuid);
    } catch (error) {
        console.error(error);
    }
}

async function deleteConverted(uuid) {
    try {
        await fetch(`/converted/${uuid}`, {
            method: 'DELETE'
        }).then(() => updateResults())
    } catch (error) {
        console.error(error);
    }
}

async function updateResults() {
    const uuids = await getUuids();
    const container = document.getElementById('conversionResults');
    container.innerHTML = '';

    function addLipsyncDiv(uuid) {
        const divLipsync = document.createElement('div');
        divLipsync.className = 'item';

        const anchorLipSync = document.createElement('a');
        anchorLipSync.href = `/converted/lipsync/${uuid}.json`;
        anchorLipSync.innerHTML = 'Download Lipsync file';

        divLipsync.appendChild(anchorLipSync);
        return divLipsync;
    }

    function addWavDiv(uuid) {
        const divWav = document.createElement('div');
        divWav.className = 'item';
        divWav.style.display = 'block'; // Set display property to block
        divWav.style.width = '100%';    // Set width to 100%

        const anchorWav = document.createElement('a');
        anchorWav.href = `/converted/wav/${uuid}.wav`;
        anchorWav.innerHTML = 'Download WAV file';

        // Create audio element for WAV playback
        const audioElement = document.createElement('audio');
        audioElement.src = `/converted/wav/${uuid}.wav`;
        audioElement.id = `audio-${uuid}`;
        audioElement.style.display = 'none'; // Hide it initially

        // Create play button to control audio element
        const playButton = document.createElement('button');
        const playSvg = `
                       <svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10">
                           <polygon points="2.5,1.5 2.5,8.5 8.5,5.0" fill="black" />
                       </svg>
                   `;
        const pauseSvg = `
                        <svg xmlns="http://www.w3.org/2000/svg" width="10" height="10" viewBox="0 0 10 10">
                          <rect x="2" y="1" width="2" height="7" fill="black"/>
                          <rect x="6" y="1" width="2" height="7" fill="black"/>
                        </svg>
                   `;

        playButton.innerHTML = playSvg;
        playButton.className = 'play-button';
        playButton.onclick = function () {
            const audio = document.getElementById(`audio-${uuid}`);
            if (audio.paused) {
                audio.play();
                playButton.innerHTML = pauseSvg;
            } else {
                audio.pause();
                playButton.innerHTML = playSvg;
            }
        };

        // Create a button element to copy UUID to clipboard
        const copyButton = document.createElement('button');
        copyButton.innerHTML = 'Copy UUID';
        copyButton.onclick = function () {
            const tempInput = document.createElement('input');
            tempInput.value = uuid;
            document.body.appendChild(tempInput);
            tempInput.select();

            navigator.clipboard
                .readText()
                .then((clipText) => {
                    document.querySelector(".editor").innerText += clipText;
                    document.body.removeChild(tempInput);
                })
                .catch((err) => {
                    console.error('Failed to copy text: ', err);
                });
        };

        // Append the elements in the desired order: anchorWav, playButton, audioElement, copyButton
        divWav.appendChild(anchorWav);
        divWav.appendChild(playButton);
        divWav.appendChild(audioElement);
        divWav.appendChild(copyButton);

        return divWav;
    }


    for (const uuid of uuids) {
        const divLipsync = addLipsyncDiv(uuid);
        const divWav = addWavDiv(uuid);

        const divButtonRemove = document.createElement('div');
        divButtonRemove.className = 'item';

        const buttonRemove = document.createElement('button');
        buttonRemove.className = 'mdc-button mdc-button--raised mdc-theme primary-bg'
        buttonRemove.innerHTML = 'Remove';
        buttonRemove.addEventListener("click", () => deleteConverted(uuid));

        divButtonRemove.appendChild(buttonRemove);

        container.appendChild(divLipsync);
        container.appendChild(divWav);
        container.appendChild(divButtonRemove);
    }
}
