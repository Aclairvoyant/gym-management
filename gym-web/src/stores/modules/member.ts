// import { defineStore } from 'pinia';
// import { getMemberListService } from '@/apis/member';
//
// export const useMemberCardStore = defineStore('memberCard', {
//     state: () => ({
//         memberCards: []
//     }),
//     actions: {
//         async fetchMemberCards() {
//             try {
//                 const response = await getMemberListService(params);
//                 if (response.data.code === 1) {
//                     this.memberCards = response.data.data;
//                     console.log('memberCards:', this.memberCards);
//                 } else {
//                     throw new Error(response.data.msg || 'Failed to fetch member cards');
//                 }
//             } catch (error) {
//                 console.error('Error fetching member cards:', error);
//             }
//         }
//     }
// });
