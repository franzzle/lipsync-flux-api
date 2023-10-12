const ProgressConversions = {
    template: `
      <div class="progress">
        <div class="progress-bar" :style="{ width: progressBarValue + '%' }">
          {{ progressBarValue }}%
        </div>
      </div>    
    `,
    data() {
        return {
            convertables: [],
        };
    },
    async created() {
        try {
            const response = await fetch('/conversion'); // Fetch the JSON data
            this.characters = await response.json(); // Parse JSON and assign to characters array
        } catch (error) {
            console.error('Error loading JSON data:', error);
        }

    }
}
