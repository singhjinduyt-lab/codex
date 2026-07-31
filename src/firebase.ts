export type UserRole = 'customer' | 'merchant' | 'driver' | 'doctor' | 'teacher' | 'support' | 'admin';

export const firebaseCollections = {
  users: 'users/{userId}',
  profiles: 'users/{userId}/profile/private',
  services: 'services/{serviceId}',
  orders: 'orders/{orderId}',
  wallets: 'wallets/{userId}',
  transactions: 'wallets/{userId}/transactions/{transactionId}',
  chats: 'chats/{chatId}/messages/{messageId}',
  posts: 'communityPosts/{postId}',
  files: 'files/{fileId}',
  notifications: 'notifications/{notificationId}',
  analytics: 'analyticsEvents/{eventId}',
} as const;

export const securityModel = {
  authentication: ['Firebase Auth email/password', 'Google Sign-In', 'Apple Sign-In', 'Phone OTP'],
  authorization: ['JWT custom claims', 'role-based Firestore rules', 'least-privilege Cloud Functions'],
  payments: ['Stripe PaymentIntents', 'PayPal Orders', 'Apple Pay', 'Google Pay', 'PCI-tokenized cards only'],
  privacy: ['field-level encryption for health records', 'signed Cloud Storage URLs', 'audit logs'],
};
