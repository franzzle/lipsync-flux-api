const FileUploader = {
  template: `
    <div>
      <input type="file" @change="uploadFile">
      <button @click="submitFile">Upload</button>
    </div>
  `,
  data() {
    return {
      selectedFile: null,
    };
  },
  methods: {
        uploadFile(event) {
            this.selectedFile = event.target.files[0];
        },
        async submitFile() {
            const formData = new FormData();
            formData.append('file', this.selectedFile);

            try {
                const response = await fetch("/conversion", {
                    method: 'POST',
                    body: formData
                });

                if (response.ok) {
                    const data = await response.json();  // Parse the JSON response
                    console.log('File uploaded successfully.');
                    this.$emit('file-uploaded', { success: true, uuid: data.uuid });  // Use data.uuid instead of response.data.uuid
                } else {
                    console.log('File upload failed.');
                }
            } catch (error) {
                console.log('An error occurred:', error);
            }
        }
  },
};