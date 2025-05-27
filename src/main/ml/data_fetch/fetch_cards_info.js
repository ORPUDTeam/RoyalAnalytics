const axios = require('axios');
const fs = require('fs').promises;
const path = require('path');
const { createObjectCsvWriter } = require('csv-writer');

const TOKEN = "TOKEN";
const BASE_URL = 'https://api.clashroyale.com/v1';
const HEADERS = { Accept: 'application/json', Authorization: `Bearer ${TOKEN}` };

const outCsv  = path.join(__dirname, 'data', 'cards_info.csv');

const csvWriter = createObjectCsvWriter({
    path: outCsv,
    header: [
        {id: 'name', title: 'name'},
        {id: 'elixirCost', title: 'elixirCost'},
        {id: 'trophies', title: 'trophies'}
    ],
    fieldDelimiter: ';',
    append: false
});

async function fetchCards() {
    const url = `${BASE_URL}/cards`;
    try {
        const response = await axios.get(url, { headers: HEADERS });
        return response.data.items;
    } catch (error) {
        console.error('Ошибка при запросе карт:', error.response?.data || error.message);
        throw error;
    }
}

async function main() {
    try {
        await fs.mkdir(path.join(__dirname, 'data'), { recursive: true });

        console.log('Запрашиваю информацию о картах...');
        const cards = await fetchCards();

        const records = cards.map(card => ({
            name: card.name,
            elixirCost: card.elixirCost,
            trophies: ''
        }));

        await csvWriter.writeRecords(records);
        console.log(`Данные сохранены в ${outCsv}`);
        console.log(`Всего карт: ${records.length}`);

    } catch (error) {
        console.error('Ошибка:', error.message);
        process.exit(1);
    }
}

main().catch(err => {
    console.error('FATAL:', err.message);
    process.exit(1);
});