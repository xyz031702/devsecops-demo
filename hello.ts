import axios from 'axios';
import _ from 'lodash';
import minimist from 'minimist';

async function main(): Promise<void> {
  // Parse CLI args (e.g. --name Alice)
  const args = minimist(process.argv.slice(2));
  const name = args.name ?? 'World';
  console.log(`Hello, ${name}!`);

  // Simple request to show axios in action
  try {
    const response = await axios.get('https://api.github.com/zen');
    console.log('GitHub Zen:', response.data);
  } catch (err) {
    console.error('Request failed:', err);
  }

  // Quick lodash demo
  const nums = [1, 2, 3, 4, 5];
  console.log('Sum of nums:', _.sum(nums));
}

main();
