const axios = require('axios');
const fs = require('fs').promises;
const path = require('path');

const TOKEN = "TOKEN";
const BASE_URL = 'https://api.clashroyale.com/v1';
const HEADERS = {
    Accept: 'application/json',
    Authorization: `Bearer ${TOKEN}`,
};

const seasons = [5, 11, 22, 33, 44, 55, 66, 77, 87, 96];

async function fetchTopPlayers(limit = 1000, season) {
    const url = `${BASE_URL}/locations/global/seasons/${season}/rankings/players?limit=${limit}`;
    const res = await axios.get(url, { headers: HEADERS });
    return res.data.items;
}

async function readExistingTags(filePath) {
    try {
        const data = await fs.readFile(filePath, 'utf8');
        return JSON.parse(data);
    } catch (err) {
        if (err.code === 'ENOENT') {
            return [];
        }
        throw err;
    }
}

async function main() {
    const dataDir = path.join(__dirname, 'data');
    await fs.mkdir(dataDir, { recursive: true });
    const outPath = path.join(dataDir, 'player_tags.json');

    let existingTags = await readExistingTags(outPath);
    const existingTagsSet = new Set(existingTags);

    for (let season of seasons) {
        console.log('Запрашиваю топ-1000 игроков сезона ' + season);
        const items = await fetchTopPlayers(1000, season);

        const newTags = items.map(p => p.tag).filter(tag => !existingTagsSet.has(tag));
        existingTags.push(...newTags);
        newTags.forEach(tag => existingTagsSet.add(tag));

        await fs.writeFile(outPath, JSON.stringify(existingTags, null, 2), 'utf8');
        console.log(`Добавлено ${newTags.length} новых тегов. Всего тегов: ${existingTags.length}`);
    }
}

main().catch(err => {
    console.error('Ошибка при запросе топ-игроков:', err.response?.data || err.message);
    process.exit(1);
});