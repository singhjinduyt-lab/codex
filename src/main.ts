import { activities, products, services } from './data/catalog';

const root = document.querySelector<HTMLDivElement>('#root');
if (!root) throw new Error('Root element not found');

const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
document.documentElement.dataset.theme = prefersDark ? 'dark' : 'light';

const serviceCards = services.slice(0, 8).map(s => `
  <article class="service-card glass" tabindex="0">
    <div class="service-icon ${s.gradient}" aria-hidden="true">${s.icon}</div>
    <div><h3>${s.title}</h3><p>${s.subtitle}</p></div>
    <footer><span>${s.eta}</span><span>${s.offline ? 'Offline cache' : 'Realtime'}</span>${s.badge ? `<b>${s.badge}</b>` : ''}</footer>
  </article>`).join('');
const productCards = products.map(p => `<article><span>${p.image}</span><div><h3>${p.name}</h3><p>${p.category} • ★ ${p.rating}</p></div><strong>${p.price}</strong></article>`).join('');
const activityCards = activities.map(a => `<article><span class="${a.status.toLowerCase()}">${a.status}</span><div><h3>${a.label}</h3><p>${a.meta}</p></div><strong>${a.amount ?? ''}</strong></article>`).join('');

root.innerHTML = `
<div class="app-shell">
  <aside class="rail glass" aria-label="Primary navigation">
    <div class="brand"><div class="logo">S</div><span>SuperNova</span></div>
    <nav>${['Home','Services','Wallet','Chat','Admin'].map((n,i)=>`<button class="${i===0?'active':''}">${n}</button>`).join('')}</nav>
    <button class="mode" id="themeToggle" aria-label="Toggle color theme">◐</button>
  </aside>
  <main>
    <header class="topbar glass">
      <button class="icon" aria-label="Open menu">☰</button>
      <label class="search">⌕<input aria-label="Search services" placeholder="Search food, rides, doctors, flights..."/>🎙️</label>
      <div class="top-actions"><button>🌎 EN • USD</button><button aria-label="Notifications">🔔</button></div>
    </header>
    <section class="hero">
      <div class="hero-copy">
        <p class="eyebrow">✦ Good morning, Maya</p>
        <h1>One beautifully connected super app for every daily need.</h1>
        <p>Order food, book rides, pay bills, chat, learn, store files and manage health from one secure, offline-capable workspace.</p>
        <div class="hero-actions"><button class="primary">Explore services</button><button class="secondary">🛡️ Security center</button></div>
      </div>
      <div class="wallet-card glass" role="group" aria-label="Digital wallet summary"><span>💳</span><span>Wallet balance</span><strong>$8,420.50</strong><small>QR, Apple Pay, Google Pay, PayPal and Stripe ready</small></div>
    </section>
    <section class="quick-grid" aria-label="Quick actions"><button class="quick glass">🍜 Order food</button><button class="quick glass">🚚 Send package</button><button class="quick glass">☀️ Weather</button><button class="quick glass">🎁 Rewards</button></section>
    <section class="section-head"><div><p class="eyebrow">Featured modules</p><h2>Production-ready service ecosystem</h2></div><button>View all ›</button></section>
    <section class="services-grid">${serviceCards}</section>
    <section class="dashboard-grid"><div class="panel glass"><div class="section-head compact"><h2>Recommended</h2><button>Personalize</button></div><div class="products">${productCards}</div></div><div class="panel glass"><div class="section-head compact"><h2>Recent activity</h2><button>History</button></div><div class="timeline">${activityCards}</div></div></section>
    <section class="admin glass"><div><p class="eyebrow">Admin dashboard</p><h2>Analytics, orders, users, reports and payments</h2><p>Role-based operations console with live metrics, fraud alerts, Cloud Functions workflows and exportable reports.</p></div><div class="metrics"><span><b>2.4M</b> Users</span><span><b>98.9%</b> Uptime</span><span><b>42ms</b> Cache hit</span></div></section>
  </main>
</div>`;

document.querySelector('#themeToggle')?.addEventListener('click', () => {
  document.documentElement.dataset.theme = document.documentElement.dataset.theme === 'dark' ? 'light' : 'dark';
});
