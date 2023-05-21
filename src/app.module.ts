import { Module } from '@nestjs/common';
import {GraphQLModule} from "@nestjs/graphql";
import {ApolloDriver, ApolloDriverConfig} from "@nestjs/apollo";
import {UserModule} from "@/apis/user/user.module";
import {AuthModule} from "@/apis/auth/auth.module";
import {IssueModule} from "@/apis/issue/issue.module";
import {CommentModule} from "@/apis/comment/comment.module";

@Module({
  imports: [
    GraphQLModule.forRoot<ApolloDriverConfig>({
      driver: ApolloDriver,
      autoSchemaFile: 'src/commons/graphql/schema.gql',
    }),
    AuthModule,
    UserModule,
    IssueModule,
    CommentModule,
  ],
})
export class AppModule {}
