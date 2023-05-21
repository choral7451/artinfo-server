export interface CreateCommentFields {
  postId: string
  parentCommentId?: string
  tag?: string
  contents: string
  userId: string
}