import React, { useMemo, useState } from 'react';
import {
  Alert,
  Linking,
  SafeAreaView,
  ScrollView,
  Share,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  View,
} from 'react-native';

const buzzWords = ['best', 'tested', 'secret', 'fast', 'mistakes', 'beginner', '2026', 'step-by-step', 'results', 'ai'];

const niches = ['Tech / AI', 'Business', 'Fitness', 'Education', 'Gaming', 'Lifestyle'];
const goals = ['Views', 'Subscribers', 'Course sales', 'Affiliate clicks'];

function tokenize(text) {
  return text.toLowerCase().replace(/[^a-z0-9\s]/g, '').split(/\s+/).filter(Boolean);
}

function topKeywords(tokens, limit = 6) {
  const counts = {};
  tokens.forEach((t) => {
    if (t.length < 4) return;
    counts[t] = (counts[t] || 0) + 1;
  });
  return Object.entries(counts)
    .sort((a, b) => b[1] - a[1])
    .slice(0, limit)
    .map(([word]) => word);
}

function titleCase(str) {
  return str.replace(/\w\S*/g, (txt) => txt[0].toUpperCase() + txt.slice(1));
}

function generateTitles(idea, keywords, audience) {
  const base = idea.length > 110 ? `${idea.slice(0, 110).trim()}…` : idea;
  const k1 = keywords[0] || 'YouTube';
  const k2 = keywords[1] || 'growth';
  return [
    `I Tried ${k1} for 30 Days — Here’s What Happened`,
    `${titleCase(k1)} vs ${titleCase(k2)}: Which Wins for ${audience}?`,
    `${titleCase(base)} (Do This Before You Post)`,
  ];
}

function scoreSeo(idea, tags, titles) {
  const lengthScore = Math.max(0, 30 - Math.abs(60 - idea.length));
  const tagScore = Math.min(tags.length * 4, 28);
  const titleScore = titles.some((t) => /\d|\?|:/.test(t)) ? 22 : 14;
  const keywordHit = buzzWords.filter((w) => idea.toLowerCase().includes(w)).length * 4;
  return Math.min(100, lengthScore + tagScore + titleScore + keywordHit);
}

function predictCtr(seoScore, titles) {
  const curiosityBoost = titles.filter((t) => /\?|happened|secret|before/i.test(t)).length * 0.5;
  const base = 2.4 + (seoScore / 100) * 6.2 + curiosityBoost;
  return Math.min(15.5, base);
}

function makeTags(keywords, niche) {
  const nicheTag = niche.toLowerCase().replace(/\s*\/\s*/g, ' ');
  const defaults = ['youtube tips', 'thumbnail design', 'video seo', 'title ideas'];
  return [...new Set([...keywords.map((k) => `${k} tutorial`), ...defaults, nicheTag])].slice(0, 12);
}

function makeThumbText(keywords) {
  const k1 = (keywords[0] || 'AI').toUpperCase();
  const k2 = (keywords[1] || 'RESULTS').toUpperCase();
  return [`${k1} TESTED`, `REAL ${k2}`, "DON'T POST YET"];
}

export default function App() {
  const [idea, setIdea] = useState('I tested 12 AI editing apps to see which one grows new YouTube channels fastest.');
  const [audience, setAudience] = useState('Beginner creators');
  const [niche, setNiche] = useState(niches[0]);
  const [goal, setGoal] = useState(goals[0]);

  const data = useMemo(() => {
    const tokens = tokenize(`${idea} ${audience} ${goal}`);
    const keywords = topKeywords(tokens);
    const tags = makeTags(keywords, niche);
    const titles = generateTitles(idea, keywords, audience);
    const thumb = makeThumbText(keywords);
    const seo = scoreSeo(idea, tags, titles);
    const ctr = predictCtr(seo, titles);
    const csvRows = titles.map((title, i) => {
      const thumbLine = thumb[i % thumb.length];
      return `"${title.replaceAll('"', '""')}","${thumbLine}","${tags.join(' | ')}"`;
    });
    const csv = `title,thumbnail_text,tags\n${csvRows.join('\n')}`;
    return { tags, titles, thumb, seo, ctr, csv };
  }, [idea, audience, niche, goal]);

  const exportToCanva = async () => {
    try {
      await Share.share({
        message: `${data.csv}\n\nPaste this CSV into Canva Bulk Create.`,
      });
      await Linking.openURL('https://www.canva.com');
    } catch (err) {
      Alert.alert('Export failed', 'Could not open share sheet or Canva link.');
    }
  };

  return (
    <SafeAreaView style={styles.safe}>
      <ScrollView style={styles.container} contentContainerStyle={styles.content}>
        <Text style={styles.title}>YouTube Title & Thumbnail Optimizer</Text>
        <Text style={styles.subtitle}>Paste video idea → titles, tags, thumbnail text + SEO score and CTR prediction.</Text>

        <View style={styles.card}>
          <Text style={styles.label}>Video Idea</Text>
          <TextInput multiline value={idea} onChangeText={setIdea} style={styles.inputLarge} placeholder="Describe your next video" placeholderTextColor="#9ea5d6" />

          <Text style={styles.label}>Audience</Text>
          <TextInput value={audience} onChangeText={setAudience} style={styles.input} placeholder="Beginner creators" placeholderTextColor="#9ea5d6" />

          <Text style={styles.label}>Niche</Text>
          <View style={styles.wrapRow}>{niches.map((item) => <Chip key={item} text={item} active={item === niche} onPress={() => setNiche(item)} />)}</View>

          <Text style={styles.label}>Goal</Text>
          <View style={styles.wrapRow}>{goals.map((item) => <Chip key={item} text={item} active={item === goal} onPress={() => setGoal(item)} />)}</View>
        </View>

        <View style={styles.kpiRow}>
          <Kpi title="SEO Score" value={`${data.seo} / 100`} />
          <Kpi title="Predicted CTR" value={`${data.ctr.toFixed(1)}%`} />
        </View>

        <View style={styles.card}>
          <Text style={styles.section}>Generated Titles</Text>
          {data.titles.map((t) => (
            <Text key={t} style={styles.bullet}>• {t}</Text>
          ))}

          <Text style={styles.section}>Thumbnail Text</Text>
          <View style={styles.wrapRow}>{data.thumb.map((t) => <Chip key={t} text={t} active />)}</View>

          <Text style={styles.section}>Tags</Text>
          <View style={styles.wrapRow}>{data.tags.map((t) => <Chip key={t} text={`#${t.replace(/\s+/g, '')}`} active={false} />)}</View>
        </View>

        <TouchableOpacity style={styles.primaryButton} onPress={exportToCanva}>
          <Text style={styles.primaryButtonText}>Export to Canva</Text>
        </TouchableOpacity>

        <View style={styles.card}>
          <Text style={styles.section}>Monetization: Creator Subscription</Text>
          <Text style={styles.bullet}>• Starter — $9/mo: 30 optimizations + Canva export.</Text>
          <Text style={styles.bullet}>• Creator Pro — $29/mo: unlimited + A/B variants.</Text>
          <Text style={styles.bullet}>• Studio — $79/mo: team workspace + API.</Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

function Chip({ text, active, onPress }) {
  return (
    <TouchableOpacity onPress={onPress} disabled={!onPress} style={[styles.chip, active ? styles.chipActive : null]}>
      <Text style={styles.chipText}>{text}</Text>
    </TouchableOpacity>
  );
}

function Kpi({ title, value }) {
  return (
    <View style={styles.kpi}>
      <Text style={styles.kpiTitle}>{title}</Text>
      <Text style={styles.kpiValue}>{value}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  safe: { flex: 1, backgroundColor: '#0f1226' },
  container: { flex: 1 },
  content: { padding: 16, paddingBottom: 36, gap: 12 },
  title: { color: '#f3f5ff', fontSize: 24, fontWeight: '700' },
  subtitle: { color: '#a9b2df', fontSize: 14, marginBottom: 6 },
  card: { backgroundColor: '#181d3a', borderWidth: 1, borderColor: '#2c366d', borderRadius: 12, padding: 12, gap: 8 },
  label: { color: '#a9b2df', fontSize: 13, marginTop: 4 },
  input: { backgroundColor: '#202751', borderColor: '#3a4685', borderWidth: 1, borderRadius: 10, color: '#f3f5ff', paddingHorizontal: 10, paddingVertical: 9 },
  inputLarge: { minHeight: 100, textAlignVertical: 'top', backgroundColor: '#202751', borderColor: '#3a4685', borderWidth: 1, borderRadius: 10, color: '#f3f5ff', paddingHorizontal: 10, paddingVertical: 9 },
  wrapRow: { flexDirection: 'row', flexWrap: 'wrap', gap: 8 },
  chip: { backgroundColor: '#28336e', borderWidth: 1, borderColor: '#3a4685', borderRadius: 999, paddingHorizontal: 10, paddingVertical: 6 },
  chipActive: { backgroundColor: '#4658c7' },
  chipText: { color: '#d8dfff', fontSize: 12 },
  section: { color: '#f3f5ff', fontWeight: '600', fontSize: 15, marginTop: 4 },
  bullet: { color: '#d8dfff', fontSize: 13, lineHeight: 20 },
  kpiRow: { flexDirection: 'row', gap: 10 },
  kpi: { flex: 1, backgroundColor: '#111633', borderWidth: 1, borderColor: '#293269', borderRadius: 10, padding: 12 },
  kpiTitle: { color: '#a9b2df', fontSize: 12 },
  kpiValue: { color: '#2dd4bf', fontSize: 20, fontWeight: '700', marginTop: 4 },
  primaryButton: { backgroundColor: '#6f83ff', borderRadius: 999, paddingVertical: 12, alignItems: 'center' },
  primaryButtonText: { color: 'white', fontWeight: '700' },
});
