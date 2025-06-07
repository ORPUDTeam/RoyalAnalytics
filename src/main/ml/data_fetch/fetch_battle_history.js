const axios = require('axios');
const fs = require('fs').promises;
const path = require('path');
const { createObjectCsvWriter } = require('csv-writer');

const TOKEN = "TOKEN";
const BASE_URL = 'https://api.clashroyale.com/v1';
const HEADERS = { Accept: 'application/json', Authorization: `Bearer ${TOKEN}` };

const tagsPath = path.join(__dirname, 'data', 'player_tags.json');
const outCsv  = path.join(__dirname, 'data', 'battle_history.csv');

const csvWriter = createObjectCsvWriter({
    path: outCsv,
    header: [
        {id: 'card1', title: 'card1'},
        {id: 'card2', title: 'card2'},
        {id: 'card3', title: 'card3'},
        {id: 'card4', title: 'card4'},
        {id: 'card5', title: 'card5'},
        {id: 'card6', title: 'card6'},
        {id: 'card7', title: 'card7'},
        {id: 'card8', title: 'card8'},
        {id: 'win',   title: 'win'}
    ],
    fieldDelimiter: ';',
    append: false
});

async function fetchBattleLog(tag) {
    const encoded = encodeURIComponent(tag);
    const url = `${BASE_URL}/players/${encoded}/battlelog`;
    const res = await axios.get(url, { headers: HEADERS });
    return res.data;
}

async function main() {
    await fs.mkdir(path.join(__dirname, 'data'), { recursive: true });

    const tagsJson = await fs.readFile(tagsPath, 'utf8');
    const playerTags = JSON.parse(tagsJson);

    const records = [];

    for (const tag of playerTags) {
        console.log(`Обработка ${tag}...`);
        try {
            const battles = await fetchBattleLog(tag);
            for (let i = 0; i < Math.min(100, battles.length); i++) {
                const m = battles[i];
                const team     = m.team[0];
                const opponent = m.opponent[0];
                const crownsP  = team.crowns;
                const crownsO  = opponent.crowns;
                if (crownsP === crownsO) continue;

                const allNames = team.cards.map(c => c.name);
                const opponentNames = opponent.cards.map(c => c.name);

                allNames.sort((a, b) => a.localeCompare(b));
                opponentNames.sort((a, b) => a.localeCompare(b))

                const deckNamesPlayer = allNames
                    .slice(0, 8)
                    .concat(Array(Math.max(0, 8 - allNames.length)).fill(''));

                const deckNamesOpponent = opponentNames
                    .slice(0, 8)
                    .concat(Array(Math.max(0, 8 - opponentNames.length)).fill(''))

                records.push({
                    card1: deckNamesPlayer[0],
                    card2: deckNamesPlayer[1],
                    card3: deckNamesPlayer[2],
                    card4: deckNamesPlayer[3],
                    card5: deckNamesPlayer[4],
                    card6: deckNamesPlayer[5],
                    card7: deckNamesPlayer[6],
                    card8: deckNamesPlayer[7],
                    win: crownsP > crownsO ? 1 : 0
                })

                records.push({
                    card1: deckNamesOpponent[0],
                    card2: deckNamesOpponent[1],
                    card3: deckNamesOpponent[2],
                    card4: deckNamesOpponent[3],
                    card5: deckNamesOpponent[4],
                    card6: deckNamesOpponent[5],
                    card7: deckNamesOpponent[6],
                    card8: deckNamesOpponent[7],
                    win: crownsO > crownsP ? 1 : 0
                });
            }
            await new Promise(r => setTimeout(r, 100));
        } catch (e) {
            console.warn(`Ошибка для ${tag}:`, e.response?.status, e.message);
        }
    }

    await csvWriter.writeRecords(records);
    console.log(`Сохранено ${records.length} записей в ${outCsv}`);
}

main().catch(err => {
    console.error('FATAL:', err.message);
    process.exit(1);
});