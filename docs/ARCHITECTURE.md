# SuperNova Super App Architecture

## Navigation
- Authentication: welcome, email/password, Google, Apple, phone OTP, forgot password and profile management.
- Main shell: Home, Services, Wallet, Chat, Community, Assistant, Health, Learning, Files, Rewards, Notifications and Admin.
- Service flows: food, grocery, medicine, taxi, package, hotel, flight, events, bills and shopping use a shared search → detail → cart/booking → checkout → live tracking pattern.

## Folder Structure
```text
src/
  components/        Reusable Material 3 surfaces, cards, buttons and empty states.
  data/              Sample catalogs and seed data.
  services/          API clients for Firebase, payments, maps, media and notifications.
  styles/            Theme tokens, responsive layout, animations and accessibility rules.
  firebase.ts        Collection paths and security capability map.
```

## Database Schema
- `users/{userId}`: identity, roles, locale, currency, accessibility preferences and notification tokens.
- `services/{serviceId}`: module metadata, availability, regions and merchandising content.
- `orders/{orderId}`: buyer, merchant, line items, status timeline, payment id and driver location.
- `wallets/{userId}` and `transactions`: balance ledger, QR payment aliases, rewards and coupons.
- `chats/{chatId}/messages`: text, voice, image and video payload metadata with delivery receipts.
- `communityPosts/{postId}`: author, media, likes, comments and shares.
- `healthRecords/{userId}/records`: encrypted documents, medicine reminders and appointments.
- `learning/{courseId}` and `progress/{userId}`: lessons, videos, completion and certificates.
- `files/{fileId}`: Cloud Storage references, owners, ACLs and virus-scan status.
- `analyticsEvents/{eventId}`: product events, funnels, admin reports and fraud signals.

## API Structure
- Cloud Functions expose `/api/v1/auth/profile`, `/api/v1/orders`, `/api/v1/payments`, `/api/v1/wallet`, `/api/v1/chat`, `/api/v1/ai`, `/api/v1/admin`.
- Realtime data uses Firestore snapshot listeners for order tracking, driver GPS, chat and notifications.
- Media uploads use resumable Cloud Storage with image optimization and signed download URLs.
- Payments use server-created intents/orders; clients never receive raw card data.

## State Management
- Local UI state is colocated in lightweight TypeScript modules and DOM event handlers.
- Server state should be wrapped in typed service hooks with optimistic updates, pagination and cache hydration.
- Offline-capable modules persist essential reads to IndexedDB/local cache and replay safe writes when online.

## Deployment
1. Install dependencies with `npm install`.
2. Configure Firebase project variables and payment provider keys.
3. Run `npm run build`.
4. Deploy static assets to Firebase Hosting and functions to Cloud Functions.
5. Enable Analytics, Crashlytics, App Check, Firestore indexes and Cloud Storage lifecycle rules.
