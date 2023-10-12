const ConversionList = {
    template: `
          <div>
            <!-- Button to refresh the list -->
            <button @click="fetchConvertables">Refresh List</button>
        
            <!-- Display the list of conversion items -->
            <ul v-if="convertables.length > 0">
              <li v-for="item in convertables" :key="item.uuid">{{ item.uuid }}</li>
            </ul>
            <p v-else>No conversion items available.</p>
          </div>
    `,
    data() {
        return {
            convertables: []
        };
    },
    methods: {
        fetchConvertables() {
            // Replace with your actual API endpoint
            // Replace with your actual API endpoint
            const apiUrl = '/conversion';

            fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                    this.convertables = data.convertables;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
            }
    }
}
