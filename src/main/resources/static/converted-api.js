async function main() {
    try {
        // Call the async function and wait for it to complete
        await updateResults();
        console.log('updateResults function completed successfully.');
    } catch (error) {
        console.error('Error:', error);
    }
}

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

    function addWavDiv(uuid) {
        const divWav = document.createElement('div');
        divWav.className = 'item';

        const anchorLipSync = document.createElement('a');
        anchorLipSync.href = `/converted/lipsync/${uuid}.json`;
        anchorLipSync.innerHTML = 'Download Lipsync file';

        const anchorWav = document.createElement('a');
        anchorWav.href = `/converted/wav/${uuid}.wav`;
        anchorWav.innerHTML = 'Download WAV file';

        // Create audio element for WAV playback
        const audioElement = document.createElement('audio');
        audioElement.src = `/converted/wav/${uuid}.wav`;
        audioElement.id = `audio-${uuid}`;
        audioElement.style.display = 'none'; // Hide it initially

        // Create play button to control audio element
        const playAnchor = document.createElement('a');
        playAnchor.href = '#'; // You can set the href attribute to the desired URL

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

        playAnchor.innerHTML = playSvg;
        playAnchor.className = 'play-button';

        playAnchor.addEventListener('click', function (event) {
            event.preventDefault(); // Prevent the default behavior of anchors
            const audio = document.getElementById(`audio-${uuid}`);
            if (audio.paused) {
                audio.play();
                playAnchor.innerHTML = pauseSvg;
            } else {
                audio.pause();
                playAnchor.innerHTML = playSvg;
            }
        });

        // Create a button element to copy UUID to clipboard

        const divCopyButton = document.createElement('div');

        const copyButton = document.createElement('button');
        copyButton.className = 'mdc-button mdc-button--raised mdc-theme primary-bg';

        const copyButtonHtml = `
            <span>Copy UUID</span>&nbsp;
            <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24" fill="#fff">
                <path d="M0 0h24v24H0z" fill="none"/>
                <path d="M16 1H4c-1.1 0-2 .9-2 2v14h2V3h12V1zm3 4H8c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h11c1.1 0 2-.9 2-2V7c0-1.1-.9-2-2-2zm0 16H8V7h11v14z"/>
            </svg>
        `;

        copyButton.innerHTML = copyButtonHtml;
        copyButton.onclick = function () {
            // Create a temporary input element to copy text to clipboard
            const tempInput = document.createElement('input');
            tempInput.value = uuid;
            document.body.appendChild(tempInput);
            tempInput.select();
            document.execCommand('copy');
            document.body.removeChild(tempInput);
        };
        divCopyButton.appendChild(copyButton);

        const buttonRemove = document.createElement('button');
        buttonRemove.className = 'mdc-button mdc-button--raised mdc-theme primary-bg'
        buttonRemove.innerHTML = 'Remove';
        buttonRemove.addEventListener("click", () => deleteConverted(uuid));

        // Append the elements in the desired order: anchorWav, playButton, audioElement, copyButton
        divWav.appendChild(wrapInDiv(anchorLipSync));
        divWav.appendChild(wrapInDiv(anchorWav));
        divWav.appendChild(wrapInDiv(playAnchor));
        divWav.appendChild(wrapInDiv(audioElement));
        divWav.appendChild(wrapInDiv(divCopyButton));
        divWav.appendChild(wrapInDiv(buttonRemove));

        return divWav;
    }

    function wrapInDiv(element) {
        const divWrapper = document.createElement('div');
        divWrapper.className = 'cell';
        divWrapper.appendChild(element);
        return divWrapper;
    }


    for (const uuid of uuids) {
        const divWav = addWavDiv(uuid);
        container.appendChild(divWav);
    }
}

main().then(() => console.log('Loading conversion result went fine.'));
