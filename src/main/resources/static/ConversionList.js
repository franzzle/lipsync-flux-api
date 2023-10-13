const ConversionList = {
    template: `
      <div :style="conversionListStyle">
        <!-- Button to refresh the list -->
        <button @click="fetchConvertables" class="btn btn-primary mb-3">Refresh List</button>
    
        <!-- Display the list of conversion items -->
        <div :style="conversionRows" v-if="convertables.length > 0">
          <div class="row mb-4" v-for="item in convertables" :key="item.uuid">
            <div class="col-md-8">{{ item.uuid }}</div>
            <div class="col-md-4">
              <button class="btn btn-success" @click="startConversion(item.uuid)">Start Conversion</button>
            </div>
          </div>
        </div>
        <p v-else>No conversion items available.</p>
      </div>
    `,
    data() {
        return {
            convertables: [],
            conversionListStyle: {
                margin: '20px'
            },
            conversionRows: {

            }
        };
    },
    methods: {
        fetchConvertables() {
            const apiUrl = '/conversion';

            fetch(apiUrl)
                .then(response => response.json())
                .then(data => {
                    this.convertables = data.convertables;
                })
                .catch(error => {
                    console.error('Error fetching data:', error);
                });
            },
        startConversion(uuid) {
            const apiUrl = `/conversion/${uuid}`;

            fetch(apiUrl, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Conversion started:', data);
                    // You can update your component state here if needed
                })
                .catch(error => {
                    console.error('Error starting conversion:', error);
                });
        }
    }
}
