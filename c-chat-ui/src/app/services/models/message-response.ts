/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

export interface MessageResponse {
  content?: string;
  createdAt?: string;
  media?: Array<string>;
  messageId?: number;
  receiverId?: string;
  senderId?: string;
  state?: 'SENT' | 'SEEN';
  type?: 'TEXT' | 'IMAGE' | 'AUDIO' | 'VIDEO';
}
