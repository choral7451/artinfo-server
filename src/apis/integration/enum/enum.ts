import {registerEnumType} from "@nestjs/graphql";

export enum UserStatus {
  ACTIVE = 'ACTIVE',
  DELETE = 'DELETE'
}
registerEnumType(UserStatus, {name: 'UserStatus'});

export enum IssueType {
  FREE= 'FREE',
  ISSUE = 'ISSUE',
  POSTSCRIPT = 'POSTSCRIPT'
}
registerEnumType(IssueType, {name: 'IssueType'});